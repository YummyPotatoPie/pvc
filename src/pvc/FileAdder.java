package pvc;

import pvc.Exceptions.AddedFileDoesNotExist;
import pvc.Exceptions.PVCException;
import pvc.Exceptions.ProcessExecutionError;
import pvc.Exceptions.RepositoryDoesNotExist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static pvc.PathsAndTokens.*;

public class FileAdder implements Handler<String> {

    public void handle(String filePath) throws PVCException {
        if (!Utilites.isRepositoryExist())
            throw new RepositoryDoesNotExist();

        File addedFile = new File(filePath);

        if (addedFile.exists()) {
            if (!isNotRepetitive(filePath)) System.exit(1);

            try {
                FileWriter writer = new FileWriter(System.getProperty("user.dir") +
                        pvcMainFolderName + "\\" + pvcAddFile, true);

                writer.write(filePath + "\n");
                writer.close();
            }
            catch (IOException ioe) {
                throw new ProcessExecutionError();
            }
        }
        else {
            throw new AddedFileDoesNotExist();
        }
    }

    private boolean isNotRepetitive(String filePath) {
        Scanner scanner = new Scanner(System.getProperty("user.dir" + pvcMainFolderName) + "\\" + pvcAddFile);

        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals(filePath)) {
                scanner.close();
                return false;
            }
        }
        scanner.close();
        return true;
    }

}
