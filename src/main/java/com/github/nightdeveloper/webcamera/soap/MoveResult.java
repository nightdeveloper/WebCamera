package com.github.nightdeveloper.webcamera.soap;

public class MoveResult {

    private String message;
    private boolean success;
    private String imageURL;

    public MoveResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "MoveResult{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
