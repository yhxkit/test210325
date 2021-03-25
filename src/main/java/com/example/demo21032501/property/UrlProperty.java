package com.example.demo21032501.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.estgames.lmstool")
@Data
public class UrlProperty {
    private String url;
    private String submit;
    private String template;
    private String eum;
    private String service;
    private String history;
    private String log;

    public String getSubmit(){
        return url + submit;
    }
    public String getTemplate(){ return url + template; }
    public String getEum(){return eum+"/sign-in?url="+url;}

    public String getService(){
        return url + service;
    }
    public String getHistory(){
        return url+history;
    }

    public String getLog(){
        return url+history+log;
    }

}
