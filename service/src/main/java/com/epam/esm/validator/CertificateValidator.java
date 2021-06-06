package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.CertificateValidationException;

/**
 * Interface for validation CertificateDto objects according to the business logic of CertificateDto
 */
public interface CertificateValidator {

    /**
     * Validates CertificateDto entity before saving in database
     *
     * @param certificate CertificateDto entity
     * @throws CertificateValidationException if CertificateDto fields  not valid
     **/
    void validateCertificateForSave(GiftCertificateDto certificate);

    /**
     * Validates CertificateDto entity before updating in database
     *
     * @param certificate CertificateDto entity
     * @throws CertificateValidationException if CertificateDto fields not valid
     **/
    void validateCertificateForUpdate(GiftCertificateDto certificate);
}

