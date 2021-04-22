package pvc;

import org.apache.commons.cli.*;
import pvc.Exceptions.PVCException;

public class CommandHandler implements Handler<CommandLine> {

    public static void main(String[] args) {
        Options options = Commands.getPVCOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("pvc", options);

            System.exit(1);
        }

        CommandHandler commandHandler = new CommandHandler();

        try {
            commandHandler.handle(cmd);
        }
        catch (PVCException pvce) {
            System.out.println(pvce.getMessage());
        }

    }

    public void handle(CommandLine cmd) throws PVCException  {
        if (cmd.hasOption(Commands.initializationOptName)) {

            RepositoryInitializer repInitializer = new RepositoryInitializer();
            repInitializer.handle(System.getProperty("user.dir"));

        }
        if (cmd.hasOption(Commands.contributorConfigOptName)) {

            ContributorConfig contConfig = new ContributorConfig();
            contConfig.handle(cmd.getOptionValues(Commands.contributorConfigOptName));

        }
        if (cmd.hasOption(Commands.addFileOptName)) {

            FileAdder fileAdder = new FileAdder();
            fileAdder.handle(cmd.getOptionValues(Commands.addFileOptName)[0]);

        }
        if (cmd.hasOption(Commands.commitOptName)) {

            Committer commiter = new Committer();
            commiter.handle("message");

        }
    }

}
