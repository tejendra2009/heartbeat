package com.oppoindia.billionbeats.data;

import com.oppoindia.billionbeats.model.UserInfo;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    UserInfo userInfo = null;

    public Result<UserInfo> login(String username, String password) {

            /*            // TODO: handle loggedInUser authentication
             *//* LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Tejendra");*//*
            ClientInterface apiService =
                    RetrofitClientInstance.getRetrofitInstance().create(ClientInterface.class);
           // final UserInfo user_info=null;

HashMap<String,String> userDetail=new HashMap<>();
userDetail.put("email",username);
userDetail.put("password",password);

            Call<UserInfo> call = apiService.userSignIn(userDetail);
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    int statusCode = response.code();
                     userInfo = response.body();
//Result result=new Result.Success<>(user_info);

                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("Response", t.toString());
                }
            });*/
return new Result.Success<>(userInfo);

    }
        public void logout () {
            // TODO: revoke authentication
        }


}
