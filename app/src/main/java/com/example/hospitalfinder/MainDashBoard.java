package com.example.hospitalfinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.telecom.ConnectionService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainDashBoard extends Fragment {

    CardView hospitalCard,docatorCard,pharmacyCard,prescriptionCard,bloodDonerCard,ambulanceCard;

    AutoCompleteTextView autoCompleteTextView;

    String [] syndromeName;
    ImageView dashBoardImage;
    int[] dashboardImages ={R.drawable.image,R.drawable.images,R.drawable.images2};

    boolean isConnected = true;

    public MainDashBoard() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setTitle("DashBoard");

        MynetConnection mynetConnection = new MynetConnection();

     //   getActivity().registerReceiver(mynetConnection, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        hospitalCard = view.findViewById(R.id.card_view_hospital);
        ambulanceCard = view.findViewById(R.id.card_view_ambulance);
        pharmacyCard = view.findViewById(R.id.card_view_parmacy);
        prescriptionCard = view.findViewById(R.id.card_view_pescription);
        bloodDonerCard = view.findViewById(R.id.card_view_blooddonner);

        autoCompleteTextView =view.findViewById(R.id.autoCompleteSyndrome);

        dashBoardImage = view.findViewById(R.id.dashboardImage);

        Random random = new Random();
        int n = random.nextInt(3);
        dashBoardImage.setBackgroundResource(dashboardImages[n]);

        LinearLayout dashboardoption = view.findViewById(R.id.dashboardOption);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animation_above);
        dashboardoption.setAnimation(animation);

          LinearLayout dashboardoptionUperText = view.findViewById(R.id.dashboardOption_uperText);
        Animation moveRL = AnimationUtils.loadAnimation(getActivity(),R.anim.move_rl_animation);
        dashboardoptionUperText.setAnimation(moveRL);
       /* LinearLayout dashboardTextUper = view.findViewById(R.id.dashboardTextUper);
        Animation moveRL1 = AnimationUtils.loadAnimation(getActivity(), R.anim.move_rl_animation);
        dashboardTextUper.setAnimation(moveRL1);
*/
         ImageView imageView = view.findViewById(R.id.dashboardImage);
        Animation animation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.animation1);
        imageView.setAnimation(animation1);
       // TextView textView = view.findViewById(R.id.dashboardText);
        Animation moveAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.move_animation);
       // textView.setAnimation(moveAnimation);

        syndromeName = getResources().getStringArray(R.array.syndromeName);

        ArrayAdapter adapter = new ArrayAdapter (getContext(),android.R.layout.simple_expandable_list_item_1,syndromeName);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name;
                name = autoCompleteTextView.getText().toString();
                Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = autoCompleteTextView.getText().toString();
               if(name.isEmpty())
               {
                   Snackbar.make(getView(),"Empty SearchBar",Snackbar.LENGTH_SHORT).show();
               }
               else {
                   if (isConnected)
                   {

                       Bundle bundle = new Bundle();
                       bundle.putString("locaitonName",name);
                       Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.NearByLocationFragment,bundle);

                   }
                   else
                   {
                       Snackbar.make(getView(),"no internet connection",Snackbar.LENGTH_SHORT).show();

                   }

               }

            }
        });




        hospitalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("hospital","hospital");
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.NearByLocationFragment,bundle);

                }
                else
                {
                    Snackbar.make(getView(),"no internet connection",Snackbar.LENGTH_SHORT).show();

                }


            }
        });

        ambulanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.ambulanceFragmnet);
            }
        });
        pharmacyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("pharmacy","pharmacy");
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.NearByLocationFragment,bundle);

                }
                else
                {
                    Snackbar.make(getView(),"no internet connection",Snackbar.LENGTH_SHORT).show();

                }

            }
        });

        prescriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.pescriptionList);

            }
        });


        bloodDonerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.bloodDonnerListFragment);
            }
        });


    }



    public class MynetConnection extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

           NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo !=null && networkInfo.isConnected())
            {
                isConnected = true;


            }
            else
            {
                isConnected = false;

            }
        }
    }

}