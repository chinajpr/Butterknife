package com.example;

import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * 类描述:注解解析器
 * 创建日期:2018/2/7 on 17:18
 * 作者:JiaoPeiRong
 */

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
    //节点处理
    private Elements elementUtils;
    //
    private Types typeUtils;
    //生成java文件
    private Filer FilerUtils;
    /**
     * 需要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //app所有注解类型成员变量 TypeElement -- MainActivity
        Map<TypeElement , List<FieldViewBinding>> targeMap = new HashMap<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)){
//            Log.i,System.out.print均不能打印出信息,因为该方法发生在编译期
            //可以通过写文件打印
            FileUtils.print("elment --->" + element.getSimpleName().toString());
        }
        return false;
    }
}
