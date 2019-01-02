package com.hua.parambuilder_compile;



import com.hua.parambuilder_annotation.Param;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author hua
 * @version V1.0
 * @date 2018/12/29 10:11
 */

public class ParamProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<String>();
        set.add(Param.class.getCanonicalName());
        return set;
    }
}
