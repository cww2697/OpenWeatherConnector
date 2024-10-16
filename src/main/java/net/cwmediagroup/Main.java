package net.cwmediagroup;

import net.cwmediagroup.config.Config;

public class Main {

    public static void main(String[] args) {
        try{
            Options options = new Options();
            options.parseArguments(args);
            Config configuration = new Config();
            if(options.generateTemplate){
                configuration.generateTemplateConfig(options.templateOutput);
                System.out.println("Generated template config: " + options.templateOutput);
                System.exit(0);
            }

            configuration.initConfiguration(options.configPath);
            Processor processor = new Processor(configuration);
            processor.processWeatherRequest();

        }catch (Throwable e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}