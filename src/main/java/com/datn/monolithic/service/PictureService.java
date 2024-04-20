package com.datn.monolithic.service;

import com.datn.monolithic.entity.Picture;
import com.datn.monolithic.payload.response.Res;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface PictureService {
    File convertToBlackAndWhite(File file) throws IOException;
    Res uploadImageToDrive(File file, String username)throws GeneralSecurityException, IOException;

}
