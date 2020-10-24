package com.khumu.android.ui.board;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khumu.android.R;
import com.khumu.android.ui.home.HomeViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class BoardFragment extends Fragment {

//    private HomeViewModel homeViewModel;
    private BoardViewModel boardViewModel;
    private ArrayList<ArticleData> articleDataArrayList;
    private ArticleAdapter articleAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // Layout inflate 이전
        // savedInstanceState을 이용해 다룰 데이터가 있으면 다룸.
        super.onCreate(savedInstanceState);
        boardViewModel = new ViewModelProvider(this).get(BoardViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 아직 ViewModel은 안 다룸.
        // homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // 나의 부모인 컨테이너에서 내가 그리고자 하는 녀석을 얻어옴. 사실상 루트로 사용할 애를 객체와.
        // inflate란 xml => java 객체
        View root = inflater.inflate(R.layout.fragment_board, container, false);

//        root.findViewById(R.id.action_navigation_board_to_navigation_home).setOnClickListener(Navigation);
        // xml 상에 recyclerview는 실질적으로 아이템이 어떻게 구현되어있는지 정의되어있지 않다.
        // linearlayout의 형태를 이용하겠다면 linearlayoutmanager을 이용한다.
        linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView = root.findViewById(R.id.recycler_view_article_list);
        recyclerView.setLayoutManager(linearLayoutManager);

//        for (int i=0; i<10; i++){
//            articleDataArrayList.add(new ArticleData(Integer.toString(i), Integer.toString(i)));
//        }
        articleDataArrayList = _mockup_data();
        articleAdapter = new ArticleAdapter(articleDataArrayList);
        recyclerView.setAdapter(articleAdapter);

        Button btn = root.findViewById(R.id.article_simple_write_btn);
        btn.setOnClickListener(v -> {
            AsyncTask<String, Void, Response> as = new AsyncTask<String, Void, Response>(){
                @Override
                protected Response doInBackground(String... strings) {
                    try{
                        boardViewModel.FetchArticles();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            as.execute();
        });
        boardViewModel.getLiveDataArticles().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArticleData>>() {
            @Override
            public void onChanged(ArrayList<ArticleData> changedSet) {
                    for(int i=articleDataArrayList.size(); i<changedSet.size(); i++){
                        articleDataArrayList.add(changedSet.get(i));
                    }
                    articleAdapter.notifyItemRangeInserted(articleDataArrayList.size(), changedSet.size());
            }
        });
        return root;
    }
    private ArrayList<ArticleData> _mockup_data(){
        ArrayList<ArticleData> l=new ArrayList<>();
//        l.add(new ArticleData("다음 예제는 주석의 내용", "안에 두 개의 연속된 하이픈이 존재하기 때문에 오류가 발생합니"));
//        l.add(new ArticleData("존재하기 때문에 오류가 발생합니", "지막의 하이픈(-)의 개"));
//        l.add(new ArticleData("하이픈은 허용하지 않습니다.", "가 있지만 종료 태그("));
//        l.add(new ArticleData("지막의 하이픈(-)의 개수는 중요하지 않습니", "주석의 내용 안에 두 개 이상의 연속된 "));
//        l.add(new ArticleData("가 없습니", "낌표(!)-->)에는 느낌표"));
//        l.add(new ArticleData("가 있지만 종료 태그(", "존재하기 때문에 오류가 발생합니"));
//        l.add(new ArticleData("다음 예제는 주석의 내용", "안에 두 개의 연속된 하이픈이 존재하기 때문에 오류가 발생합니"));
//        l.add(new ArticleData("존재하기 때문에 오류가 발생합니", "지막의 하이픈(-)의 개"));
//        l.add(new ArticleData("하이픈은 허용하지 않습니다.", "가 있지만 종료 태그("));
//        l.add(new ArticleData("지막의 하이픈(-)의 개수는 중요하지 않습니", "주석의 내용 안에 두 개 이상의 연속된 "));
//        l.add(new ArticleData("가 없습니", "낌표(!)-->)에는 느낌표"));
//        l.add(new ArticleData("가 있지만 종료 태그(", "존재하기 때문에 오류가 발생합니"));
        return l;
    }
}