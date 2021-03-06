package com.project.zhimer.studentdesk.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.project.zhimer.studentdesk.R;
import com.project.zhimer.studentdesk.SessionManager;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DaftarWisuda extends Fragment {

    View view;
    SessionManager sessionManager;

    //biodata
    TextView mahasiswa_nama, mahasiswa_nim, mahasiswa_prodi, mahasiswa_telp, mahasiswa_phone, mahasiswa_alamat, mahasiswa_kota, mahasiswa_kodePos;

    //data skripsi
    TextView pembimbing1, pembimbing2, judulSkripsi, ukuranToga, saran, namaPerusahaan, bidang, kesesuaian;

    LinearLayout directWebsite, dataWisuda;

    ProgressView progressView;


    public DaftarWisuda() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_daftar_wisuda, container, false);

        progressView = view.findViewById(R.id.circular);

        mahasiswa_nama = view.findViewById(R.id.mahasiswa_nama);
        mahasiswa_nim = view.findViewById(R.id.mahasiswa_nim);
        mahasiswa_prodi = view.findViewById(R.id.mahasiswa_prodi);
        mahasiswa_telp = view.findViewById(R.id.mahasiswa_telp);
        mahasiswa_phone = view.findViewById(R.id.mahasiswa_phone);
        mahasiswa_alamat = view.findViewById(R.id.mahasiswa_alamat);
        mahasiswa_kota = view.findViewById(R.id.mahasiswa_kota);
        mahasiswa_kodePos = view.findViewById(R.id.mahasiswa_kodePos);

        pembimbing1 = view.findViewById(R.id.tvPembimbing1);
        pembimbing2 = view.findViewById(R.id.tvPembimbing2);
        judulSkripsi = view.findViewById(R.id.tvJudulSkripsi);
        ukuranToga = view.findViewById(R.id.tvUkuranToga);
        saran = view.findViewById(R.id.tvSaran);
        namaPerusahaan = view.findViewById(R.id.tvPerusahaan);
        bidang = view.findViewById(R.id.tvBidang);
        kesesuaian = view.findViewById(R.id.tvKesesuaian);

        directWebsite = view.findViewById(R.id.directWebsite);
        dataWisuda = view.findViewById(R.id.dataWisuda);


        sessionManager = new SessionManager(getContext());

        progressView.setVisibility(View.VISIBLE);
        progressView.start();

        GetDataMahasiswa();
        GetDataWisuda();

        return view;
    }

    private void GetDataMahasiswa() {
        String url = sessionManager.getUrl() + "/biodata/LihatBiodata/format/json";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.setBasicAuth(sessionManager.getAuthUsername(), sessionManager.getAuthPassword());
        params.put("uname", sessionManager.getNim());
        params.put("pwd", sessionManager.getPassword());
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String nama = object.getString("mhs_nm");
                        String nim = object.getString("mhs_nim");
                        String prodi = object.getString("NamaProgdi");
                        String telp = object.getString("mhs_telepon");
                        String phone = object.getString("mhs_hp");
                        String alamat = object.getString("mhs_alm");
                        String kota = object.getString("mhs_kota");
                        String kode = object.getString("kodepos");

                        mahasiswa_nama.setText(nama);
                        mahasiswa_nim.setText(nim);
                        mahasiswa_prodi.setText(prodi);
                        mahasiswa_telp.setText(telp);
                        mahasiswa_phone.setText(phone);
                        mahasiswa_alamat.setText(alamat);
                        mahasiswa_kota.setText(kota);
                        mahasiswa_kodePos.setText(kode);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void GetDataWisuda() {
        String url = sessionManager.getUrl() + "/wisuda/DataWisuda/format/json";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.setBasicAuth(sessionManager.getAuthUsername(), sessionManager.getAuthPassword());
        params.put("uname", sessionManager.getNim());
        params.put("pwd", sessionManager.getPassword());
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject objectData = jsonObject.getJSONObject("data");
                    JSONObject objectStat = objectData.getJSONObject("status");

                    String status = objectStat.getString("status");
                    if (status.equals("sudah_daftar")) {
                        directWebsite.setVisibility(View.GONE);
                        dataWisuda.setVisibility(View.VISIBLE);
                    }

                    JSONObject objectDatas = objectData.getJSONObject("data");

                    String pembimbingSatu = objectDatas.getString("pembimbing1");
                    String pembimbingDua = objectDatas.getString("pembimbing2");
                    String judul = objectDatas.getString("judulSkripsi");
                    String toga = objectDatas.getString("ukuranToga");
                    String saranKurikulum = objectDatas.getString("saran_kurikulum");
                    String perusahaan = objectDatas.getString("tempatKerja");
                    String BidangPekerjaan = objectDatas.getString("bidangKerja");
                    String sesuai = objectDatas.getString("kesesuaian_bidang_pekerjaan");




                    pembimbing1.setText(pembimbingSatu);
                    pembimbing2.setText(pembimbingDua);
                    judulSkripsi.setText(judul);
                    ukuranToga.setText(toga);
                    saran.setText(saranKurikulum);
                    namaPerusahaan.setText(perusahaan);
                    bidang.setText(BidangPekerjaan);
                    kesesuaian.setText(sesuai);

                    progressView.setVisibility(View.GONE);
                    progressView.stop();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Toast.makeText(getContext(), "Koneksi internet anda bermasalah\nSilahkan coba beberapa saat lagi", Toast.LENGTH_LONG).show();
            }
        });

    }

}
