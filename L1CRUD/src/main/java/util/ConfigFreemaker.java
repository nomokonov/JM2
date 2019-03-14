package util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class ConfigFreemaker {
    private static Configuration configuration ;
    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        //First loader
        ClassTemplateLoader classLoader = new ClassTemplateLoader(
                new ConfigFreemaker().getClass(), "/templates");
        //Set to Configuration
        configuration.setTemplateLoader(classLoader);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    public static Configuration getConfiguration() {
        return configuration;
    }
}
