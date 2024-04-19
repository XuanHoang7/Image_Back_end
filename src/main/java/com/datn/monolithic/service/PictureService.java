package com.datn.monolithic.service;

import com.datn.monolithic.entity.Picture;
import com.datn.monolithic.payload.response.Res;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public interface PictureService {
    String convertToBlackAndWhite(Picture picture) throws IOException;
    public Res uploadImageToDrive(File file)throws GeneralSecurityException, IOException;
}
