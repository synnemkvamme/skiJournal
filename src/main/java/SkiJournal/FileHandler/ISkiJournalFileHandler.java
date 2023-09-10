package SkiJournal.FileHandler;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.Collection;

import SkiJournal.Profile;

public interface ISkiJournalFileHandler {
    Profile readProfile(String filename, Profile profile) throws FileNotFoundException;
    Profile writeProfile(String filename, Profile profile) throws FileNotFoundException;
    void createNewFile(String filename, Profile profile) throws FileNotFoundException, FileAlreadyExistsException;
    Collection<String> getUserList();
    String getFilePath(String filename);
}
