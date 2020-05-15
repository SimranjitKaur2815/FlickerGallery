package com.github.flickergallery.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class SearchFragment extends Fragment {
    View view;
    RequestQueue requestQueue;
    Context context;
    String TAG="Gallery Fragment";
    EditText search_text;
    ProgressBar searchProgress;
    TextView nothingFound;
    List<CommonPickModel> searchModelList=new ArrayList<>();
    RecyclerView recyclerView;
    String search;
    ImageView searchIcon;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view=inflater.inflate(R.layout.activity_search_fragment,container,false);
       init();

       return view;
    }

    private void initElements() {

        requestQueue= Volley.newRequestQueue(context);
        search_text=view.findViewById(R.id.search_text);
        searchProgress=view.findViewById(R.id.searchProgress);
        nothingFound=view.findViewById(R.id.nothingFound);
        recyclerView=view.findViewById(R.id.rv_search);
        searchIcon=view.findViewById(R.id.searchIcon);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void init() {
        initElements();
        initListeners();
    }

    private void initListeners() {
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "beforeTextChanged: "+charSequence );

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e(TAG, "onTextChanged: "+charSequence  );
                search=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e(TAG, "afterTextChanged: "+editable );
            }
        });
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getServerData(search);
            }
        });
    }

    private void getServerData(String searchText) {
        searchModelList.clear();
        searchProgress.setVisibility(View.VISIBLE);
        String search_url = ApiUrls.flicker_search+"&text="+searchText;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, search_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject photosObject=response.getJSONObject("photos");
                    JSONArray photoArray=photosObject.getJSONArray("photo");
                    if(photoArray.length()==0){
                        nothingFound.setVisibility(View.VISIBLE);
                    }
                    else{
                    for (int i = 0; i < photoArray.length(); i++) {
                        JSONObject photosObj=photoArray.getJSONObject(i);
                        CommonPickModel searchModel=new CommonPickModel();
                        searchModel.setImage(photosObj.getString("url_s"));
                        searchModelList.add(searchModel);
                    }

                    }
                    GalleryAdapter adapter=new GalleryAdapter(searchModelList,context);
                    recyclerView.setAdapter(adapter);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        searchProgress.setVisibility(View.GONE);
    }
}
