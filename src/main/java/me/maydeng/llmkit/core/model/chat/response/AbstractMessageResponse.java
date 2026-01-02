package me.maydeng.llmkit.core.model.chat.response;

public abstract class AbstractMessageResponse<T> {
    protected boolean error;
    protected String errorMessage;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract T getMessage();
}
