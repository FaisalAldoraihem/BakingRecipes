package com.faisal.bakingrecipes.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final String MOVIE_REQUEST_API
            = "https://d17h27t6h515a5.cloudfront.net";

    private static Retrofit retrofit;

   /* static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor()) // This is used to add ApplicationInterceptor.
            .addNetworkInterceptor(new CustomInterceptor()) //This is used to add NetworkInterceptor.
            .build();*/

    public static Retrofit getRetrofitClient() {

        if (retrofit == null) {
            /*HttpLoggingInterceptor interceptor  = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
*/
            retrofit = new Retrofit.Builder()
                    .baseUrl(MOVIE_REQUEST_API)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}
