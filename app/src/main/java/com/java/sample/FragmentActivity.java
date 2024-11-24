package com.java.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.java.sample.contract.TransformData;
import com.java.sample.fragment.FragA;
import com.java.sample.fragment.FragB;
import com.java.sample.fragment.FragC;
import com.java.sample.fragment.FragD;
import com.java.sample.fragment.FragDialog;
import com.java.sample.fragment.FragE;
import com.java.sample.fragment.FragF;
import com.java.sample.fragment.FragG;
import com.java.sample.fragment.FragmentA;
import com.java.sample.fragment.FragmentB;
import com.java.sample.util.Observer;
import com.java.sample.util.OnEventController;


public class FragmentActivity extends AppCompatActivity implements TransformData {

    TextView lblFragment;
    Button btnChangeTitleFragment, btnFragDialog;

    FragmentManager fragmentManager = getSupportFragmentManager();


    int TEST_EVENT_A = 1111;
    int TEST_EVENT_B = 2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initUIElements();

        // TODO: 1. Register.Listening events
        Observer.INSTANCE.register(new OnEventController() {
//            @Override
//            public void onEvent(int eventType, View view, Object data) {
//                String message = "[1] Event type: " + String.valueOf(eventType) + " - data: " + data;
//                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onEvent(int eventType, View view, Object data, OnEventController eventController) {
//                String message = "[2] Event type: " + String.valueOf(eventType) + " - data: " + data;
//                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
//            }

//            @Override
//            public void onEvent(int eventType, View view, String data) {
//                String message = "[1] Event type: " + String.valueOf(eventType) + " - data: " + data;
//                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onEvent(int eventType, View view, String data, OnEventController<String> eventController) {
//                String message = "[2] Event type: " + String.valueOf(eventType) + " - data: " + data;
//                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
//            }

            @Override
            public <string> void onEvent(int eventType, View view, string data) {
                String message = "[2] Event type: " + String.valueOf(eventType) + " - data: " + data;
                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public <string> void onEvent(int eventType, View view, string data, OnEventController eventController) {
                String message = "[2] Event type: " + String.valueOf(eventType) + " - data: " + data;
                Toast.makeText(FragmentActivity.this, message, Toast.LENGTH_SHORT).show();
            }

        });

        // Change data from activity to fragment
        btnChangeTitleFragment.setOnClickListener(view -> {
            try {
                // 1. Using FrameLayout
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentLayout);
                if (fragment != null) {
                    if (fragment instanceof FragmentA) {
                        FragmentA fragmentA = (FragmentA) fragment;
                        fragmentA
                                .setTitle("Title from activity")
                                .setName("Name A from activity")
                                .onClick((String name) -> {
                                    // TODO: Update Activity Title (from fragment A to activity)
                                    lblFragment.setText(name);
                                    // TODO: 2. Publish event A
                                    Observer.INSTANCE.publish(TEST_EVENT_A, name);
                                    return null;
                                });
                    } else if (fragment instanceof FragmentB) {
                        FragmentB fragmentB = (FragmentB) fragment;
                        fragmentB
                                .setTitle("Title from activity")
                                .setName("Name B from activity")
                                .onClick(name -> {
                                    // TODO: Update Activity Title (from fragment B to activity)
                                    lblFragment.setText(name);
                                    // TODO: 2. Publish event B
                                    Observer.INSTANCE.publish(TEST_EVENT_B, name);
                                    return null;
                                });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error (btnChangeTitleFragment) ===> " + e.getMessage());
            }
        });

        // Show Fragment Dialog
        btnFragDialog.setOnClickListener(view -> {
            FragDialog fragDialog = new FragDialog();
            fragDialog.show(fragmentManager, "dialog");
        });
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void showFragment(View view) {
        Fragment fragment = null;
        if (view.getId() == R.id.btnShowFragmentA) {
            fragment = new FragmentA();
        } else if (view.getId() == R.id.btnShowFragmentB) {
            fragment = new FragmentB();
        } else {
            Toast.makeText(this, "Fragment not found", Toast.LENGTH_SHORT).show();
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.add(R.id.fragmentLayout, fragment);

            // TODO: Add data to fragment
            Bundle bundle = new Bundle();
            bundle.putString("test", "Testing");
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragmentLayout, fragment);
            fragmentTransaction.commit();

            // 1. Using FrameLayout
            if (fragment instanceof FragmentA) {
                FragmentA fragmentA = (FragmentA) fragment;
                fragmentA
                        .setTitle("Title from activity")
                        .setName("Name A from activity")
                        .onClick((String name) -> {
                            // TODO: Update Activity Title (from fragment A to activity)
                            lblFragment.setText(name);
                            // TODO: 2. Publish event A
                            Observer.INSTANCE.publish(TEST_EVENT_A, name);
                            return null;
                        });
            } else if (fragment instanceof FragmentB) {
                FragmentB fragmentB = (FragmentB) fragment;
                fragmentB
                        .setTitle("Title from activity")
                        .setName("Name B from activity")
                        .onClick(name -> {
                            // TODO: Update Activity Title (from fragment B to activity)
                            lblFragment.setText(name);
                            // TODO: 2. Publish event B
                            Observer.INSTANCE.publish(TEST_EVENT_B, name);
                            return null;
                        });
            }
        }
    }

    public void addFragA(View view) {
        String tag = FragA.class.getSimpleName();
        System.out.println("addFragA ===> " + tag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragA(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragB(View view) {
        String tag = FragB.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragB(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragC(View view) {
        String tag = FragC.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragC(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragD(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String tag = FragD.class.getSimpleName();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragD(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragE(View view) {
        String tag = FragE.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragE(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragF(View view) {
        String tag = FragF.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragF(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void addFragG(View view) {
        String tag = FragG.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentLayoutAddRemove, new FragG(), tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    public void backFrag(View view) {
        fragmentManager.popBackStack();
    }

    public void removeFragA(View view) {
        String tag = FragA.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragA fragA = (FragA) fragmentManager.findFragmentByTag(tag);
        if (fragA != null) {
            fragmentTransaction.remove(fragA);
        }
        fragmentTransaction.commit();
    }
    public void removeFragB(View view) {
        String tag = FragB.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragB frag = (FragB) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }
    public void removeFragC(View view) {
        String tag = FragC.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragC frag = (FragC) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }
    public void removeFragD(View view) {
        String tag = FragD.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragD frag = (FragD) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }
    public void removeFragE(View view) {
        String tag = FragE.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragE frag = (FragE) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }
    public void removeFragF(View view) {
        String tag = FragF.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragF frag = (FragF) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }
    public void removeFragG(View view) {
        String tag = FragG.class.getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragG frag = (FragG) fragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            fragmentTransaction.remove(frag);
        }
        fragmentTransaction.commit();
    }

    public void popFrag(View view) {
        String tag = FragA.class.getSimpleName();
        System.out.println("popFrag ===> " + tag);
        fragmentManager.popBackStack(tag, 0);
    }

    private void initUIElements() {
        lblFragment = findViewById(R.id.lblFragment);
        btnChangeTitleFragment = findViewById(R.id.btnChangeTitleFragment);
        btnFragDialog = findViewById(R.id.btnFragDialog);

        // 2. Using FragmentContainerView (FCV)
        FragmentA fragmentA = (FragmentA) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerViewA);
        FragmentB fragmentB = (FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerViewB);

        // Attach events for FragmentA
        if (fragmentA != null) {
            fragmentA
                    .setTitle("Change from activity (FCV)")
                    .setName("Name A from activity (FCV)")
                    .onClick(name -> {
                        // TODO: Update Activity Title (from fragment A to activity)
                        lblFragment.setText(name);
                        // TODO: 2. Publish event A
                        Observer.INSTANCE.publish(TEST_EVENT_A, name);
                        return null;
                    });
        }

        // Attach events for FragmentB
        if (fragmentB != null) {
            fragmentB
                    .setTitle("Change from activity (FCV)")
                    .setName("Name B from activity (FCV)")
                    .onClick(name -> {
                        // TODO: Update Activity Title (from fragment B to activity)
                        lblFragment.setText(name);
                        // TODO: 2. Publish event B
                        Observer.INSTANCE.publish(TEST_EVENT_B, name);
                        return null;
                    });
        }
    }

    @Override
    public void requireDelete(Boolean flag) {
        if (flag) {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show();
        }
    }
}