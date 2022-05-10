package com.example.carfixsystem;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;


@MapperScan("com/example/carfixsystem/mapper")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class})
@EnableSwagger2
@Slf4j

//@EnableTransactionManagement
public class CarFixSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarFixSystemApplication.class, args);
        System.out.println("hot-fix change1");
        /*FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/carfixsystemdb?useUnicode=true&characterEncoding=utf-8", "root", "Aa135798")
                .globalConfig(builder -> {
                    builder.author("张彬") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\31744\\Desktop\\MyCode"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.carfixsystem") // 设置父包名
                            //.moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\Users\\31744\\Desktop\\MyCode")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("order_info");
                    // 设置需要生成的表名
                          //  .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
               .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();*/
    }
//    @Bean
//    public ObjectMapper serializingObjectMapper() {
//        JavaTimeModule module = new JavaTimeModule();
//        LocalDateTimeDeserializer dateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        module.addDeserializer(LocalDateTime.class, dateTimeDeserializer);
//        return Jackson2ObjectMapperBuilder.json().modules(module)
//                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();
//    }

}
