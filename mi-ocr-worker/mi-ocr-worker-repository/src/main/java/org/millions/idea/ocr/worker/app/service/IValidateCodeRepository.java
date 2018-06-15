package org.millions.idea.ocr.worker.app.service;

public interface IValidateCodeRepository {
    String getImageText(byte[] data);
}
