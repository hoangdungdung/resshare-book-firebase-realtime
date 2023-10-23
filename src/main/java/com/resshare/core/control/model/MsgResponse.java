package com.resshare.core.control.model;

/**
 * Created by  hoangdung1008@gmail.com on 16/06/2019.
 */
public class MsgResponse {


    private String exception_msg;
    private String message_success;
    private boolean success;
    private boolean upload_image;

    private String  image_path;
    private String referenceImagePath;


    // Getter Methods

    public String getException_msg() {
        return exception_msg;
    }

    public String getMessage_success() {
        return message_success;
    }

    public boolean getSuccess() {
        return success;
    }

    public boolean getUpload_image() {
        return upload_image;
    }

    public String   getImage_path() {
        return image_path;
    }

    // Setter Methods

    public void setException_msg(String exception_msg) {
        this.exception_msg = exception_msg;
    }

    public void setMessage_success(String message_success) {
        this.message_success = message_success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUpload_image(boolean upload_image) {
        this.upload_image = upload_image;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }


    public void setReferenceImagePath(String referenceImagePath) {
        this.referenceImagePath=referenceImagePath;
    }


    public String getReferenceImagePath() {
        return referenceImagePath;
    }


}