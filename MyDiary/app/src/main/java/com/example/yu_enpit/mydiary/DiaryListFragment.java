package com.example.yu_enpit.mydiary;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiaryListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiaryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiaryListFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    private OnFragmentInteractionListener mListener;
    private Realm mRealm;

    public DiaryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiaryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiaryListFragment newInstance(String param1, String param2) {
        DiaryListFragment fragment=new DiaryListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm=Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_diary_list, container, false);
        RecyclerView recyclerView= (RecyclerView) v.findViewById(R.id.recycler);

        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        RealmResults<Diary> diaries=mRealm.where(Diary.class).findAll();
        DiaryRealmAdapter adapter=new DiaryRealmAdapter(getActivity(), diaries, true);
        recyclerView.setAdapter(adapter);

        return v;
        // Inflate the layout for this fragment

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_diary_list, menu);
        MenuItem addDiary=menu.findItem(R.id.menu_item_add_diary);
        MenuItem deleteAll=menu.findItem(R.id.menu_item_delete_all);
        MyUtils.tintMenuIcon(getContext(), addDiary, android.R.color.white);
        MyUtils.tintMenuIcon(getContext(), deleteAll, android.R.color.white);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_add_diary:
                if(mListener!=null) mListener.onAddDiarySelcted();
                return true;
            case R.id.menu_item_delete_all:
                final RealmResults<Diary> diaries=mRealm.where(Diary.class).findAll();
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        diaries.deleteAllFromRealm();
                    }
                });
                return true;
        }
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddDiarySelcted();
    }
}
