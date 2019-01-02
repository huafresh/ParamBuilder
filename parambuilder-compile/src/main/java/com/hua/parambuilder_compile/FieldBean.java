package com.hua.parambuilder_compile;

import com.hua.parambuilder_annotation.Param;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

/**
 * @author hua
 * @version V1.0
 * @date 2019/1/2 13:57
 */

class FieldBean {
    String name;
    TypeName typeName;

    FieldSpec createFieldSpec() {
        return FieldSpec.builder(typeName, name, Modifier.PRIVATE).build();
    }

    MethodSpec createSetterMethodSpec(TypeName returnType) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addParameter(typeName, name)
                .addStatement("this.$N = $N", name, name)
                .addStatement("return this")
                .build();
    }
}
