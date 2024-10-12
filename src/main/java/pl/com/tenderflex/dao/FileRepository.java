package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.File;

public interface FileRepository {

    File create(File file);
    
}