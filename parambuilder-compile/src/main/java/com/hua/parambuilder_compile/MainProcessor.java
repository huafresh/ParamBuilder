package com.hua.parambuilder_compile;

import com.google.auto.service.AutoService;
import com.hua.parambuilder_annotation.Builder;
import com.hua.parambuilder_annotation.Param;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * @author hua
 * @version V1.0
 * @date 2018/12/29 10:11
 */
@AutoService(Processor.class)
public class MainProcessor extends AbstractProcessor {
    private List<TargetParamFile> targets = null;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment environment) {
        ParamBuilderFile paramBuilder = null;
        List<IJavaFile> javaFiles = null;

        Set<? extends Element> elements = environment.getElementsAnnotatedWith(Builder.class);
        for (Element element : elements) {
            //collect all fields annotated by @Param
            List<FieldBean> fields = null;
            List<? extends Element> enclosedElements = element.getEnclosedElements();
            for (Element enclosedElement : enclosedElements) {
                Param annotation = enclosedElement.getAnnotation(Param.class);
                if (annotation != null) {
                    if (fields == null) {
                        fields = new ArrayList<>();
                    }
                    FieldBean field = new FieldBean();
                    field.name = enclosedElement.getSimpleName().toString();
                    field.typeName = TypeName.get(enclosedElement.asType());
                    fields.add(field);
                }
            }

            if (javaFiles == null) {
                javaFiles = new ArrayList<>();
            }

            //"XXX_ParamBuilder_Param.java"
            TargetBean targetBean = createTargetBean(element, fields);
            TargetParamFile targetParam = new TargetParamFile(targetBean);
            javaFiles.add(targetParam);

            //"ParamBuilder.java"
            if (paramBuilder == null) {
                paramBuilder = new ParamBuilderFile();
                javaFiles.add(paramBuilder);
            }
            paramBuilder.addMethod(targetBean.getStaticMethodName(), targetBean);

            //"XXX_ParamBuilder_Injection.java"
            if (targetBean.targetType != TargetType.OBJECT) {
                TargetInjectionFile injectionFile = new TargetInjectionFile(targetBean);
                javaFiles.add(injectionFile);
            }
        }

        //createMethod java file
        if (javaFiles != null) {
            try {
                for (IJavaFile iJavaFile : javaFiles) {
                    JavaFile javaFile = iJavaFile.create();
                    javaFile.writeTo(filer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private TargetBean createTargetBean(Element element, List<FieldBean> fields) {
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        String annClassName = ((TypeElement) element).getSimpleName().toString();
        TargetType targetType = resolveTargetType((TypeElement) element);
        Builder ann = element.getAnnotation(Builder.class);
        String staticMethodName = ann.staticMethodName();
        String createMethodName = ann.createMethodName();
        if (staticMethodName.isEmpty()) {
            if (targetType == TargetType.ACTIVITY) {
                staticMethodName = "start" + annClassName;
            } else {
                staticMethodName = "new" + annClassName;
            }
        }
        if (createMethodName.isEmpty()) {
            if (targetType == TargetType.ACTIVITY) {
                createMethodName = "start";
            } else {
                createMethodName = "create";
            }
        }

        return new TargetBean(packageName,
                element,
                createMethodName,
                fields,
                targetType,
                staticMethodName);
    }

    private TargetType resolveTargetType(TypeElement element) {
        TypeMirror superclass = element.getSuperclass();
        if (!(superclass instanceof NoType)) {
            Element superElement = ((DeclaredType) superclass).asElement();
            TypeName superTypeName = TypeName.get(superclass);
            if (Constants.ACTIVITY_CLASS_NAME.equals(superTypeName) ||
                    Constants.ACTIVITY_COMPAT_CLASS_NAME.equals(superTypeName)) {
                return TargetType.ACTIVITY;
            } else if (Constants.FRAGMENT_V4_CLASS_NAME.equals(superTypeName)) {
                return TargetType.FRAGMENT;
            } else {
                return resolveTargetType((TypeElement) superElement);
            }
        }
        return TargetType.OBJECT;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<String>();
        set.add(Builder.class.getCanonicalName());
        set.add(Param.class.getCanonicalName());
        return set;
    }

}
