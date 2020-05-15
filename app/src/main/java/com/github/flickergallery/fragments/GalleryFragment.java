package com.github.flickergallery.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.flickergallery.R;
import com.github.flickergallery.adapters.GalleryAdapter;
import com.github.flickergallery.models.CommonPickModel;
import com.github.flickergallery.utilities.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    View view;
    Context context;
    List<CommonPickModel> commonPickModelList =new ArrayList<>();
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    String TAG="Gallery Fragment";
    ImageView previous_page,next_page;
    TextView page_count;
    ProgressBar progressBar;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_gallery_fragment,container,false);
       init();
       getServerData(1);


       return view;
    }

    private void init() {
        initElements();
        initListeners();
    }

    private void initListeners() {
        previous_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x=page_count.getText().toString();
                int i=Integer.parseInt(x);
                if(i==1){
                    previous_page.setEnabled(false);
                } else{
                    int y=i-1;
                    String  previous=String.valueOf(y);
                    page_count.setText(previous);
                    getServerData(y);
                }
            }
        });

        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous_page.setEnabled(true);
                String x=page_count.getText().toString();
                int i=Integer.parseInt(x);
                int b=i+1;
                String next=String.valueOf(b);
                page_count.setText(next);
                getServerData(b);


            }
        });
    }

    private void initElements() {
        recyclerView=view.findViewById(R.id.rv_gallery);
        previous_page=view.findViewById(R.id.previous_page);
        next_page=view.findViewById(R.id.next_page);
        page_count=view.findViewById(R.id.page_count);
        progressBar=view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        requestQueue= Volley.newRequestQueue(context);
        page_count.setText("1");
    }

    private void getServerData(int page_number) {
        progressBar.setVisibility(View.VISIBLE);
        commonPickModelList.clear();
        recyclerView.setAdapter(null);
        String images_api = ApiUrls.flicker_url+"&page="+page_number;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, images_api, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: "+response );
                try {
                    JSONObject jsonObject=response.getJSONObject("photos");
                    JSONArray jsonArray=jsonObject.getJSONArray("photo");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        CommonPickModel commonPickModel =new CommonPickModel();
                        commonPickModel.setImage(jsonObject1.getString("url_s"));
                        commonPickModelList.add(commonPickModel);
                    }
                    GalleryAdapter galleryAdapter=new GalleryAdapter(commonPickModelList,context);
                    recyclerView.setAdapter(galleryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "+error.getMessage() );
                progressBar.setVisibility(View.GONE);
            }
        }/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //for post req
                return super.getParams();
            }}
        */);
        requestQueue.add(jsonObjectRequest);
    }
}
