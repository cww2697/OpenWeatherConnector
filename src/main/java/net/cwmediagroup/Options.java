package net.cwmediagroup;

import org.jetbrains.annotations.NotNull;

public class Options {

    public boolean generateTemplate = false;
    public String configPath = "";
    public String templateOutput = "";

    public void parseArguments(String @NotNull [] args){
        int argCounter=0;

        switch(args[argCounter]){
            case "-t":
            case "--template":
                if (args.length>argCounter+1){
                    if (args[argCounter+1].charAt(0) == '-') {
                        throw new IllegalArgumentException("Invalid option: \"" + args[argCounter + 1] + "\" Expecting path for output file.");
                    } else {
                        this.generateTemplate = true;
                        this.templateOutput = args[argCounter+1];
                    }
                } else {
                    this.generateTemplate = true;
                    this.templateOutput = "config.json";
                }

                break;

            case "-c":
            case "--config":
                if (args[argCounter+1].charAt(0) == '-'){
                    throw new IllegalArgumentException("Invalid option: \""+args[argCounter+1]+"\" Expecting path to configuration file.");
                } else {
                    this.configPath = args[argCounter+1];
                }
                break;

            case "-h":
            case "--help":
                String help = """
                        Weather API Connector
                        
                        Usage:
                        -t, --template <path>    Specify the path for output file template. Example: -t /path/to/template
                        -c, --config <path>      Specify the path to the configuration file. Example: -c /path/to/config
                        
                        If no argument is provided, a default configuration matching the configuration template will be used.
                        The default configuration will output in the terminal and provide weather details for Los Angeles, CA as an example.
                        """;
                System.out.println(help);
                System.exit(0);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized option: " + args[argCounter]);
            }
    }
}
