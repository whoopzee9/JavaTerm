package com.example.clientjavaterm;

public class ExecuteNetworkOperation extends Thread { //TODO перенести класс в другой файл

    private RequestHandler requestHandler;
    private String resultJson;
    private boolean success;

    public ExecuteNetworkOperation(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        success = false;
        resultJson = null;
    }

    /*@Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Display the progress bar.
        //findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            resultJson = apiAuthenticationClient.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Hide the progress bar.
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        // Login Success
        //System.out.println("in thread: " + resultJson);
        if (resultJson != null) {
            startMainActivityAndClose(resultJson);
            //System.out.println("not null!");
            //success = true;
        }
        // Login Failure
        else {
            Toast.makeText(getApplicationContext(), "Invalid username or password!",
                    Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    public void run() {
        //resultJson = apiAuthenticationClient.execute(); //todo исключения
    }



    public String getResultJson() {
        return resultJson;
    }

}
