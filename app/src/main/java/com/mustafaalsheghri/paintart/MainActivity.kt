package com.mustafaalsheghri.paintart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.ads.consent.*
import com.google.android.gms.ads.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.mustafaalsheghri.paintart.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    lateinit var form: ConsentForm
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    lateinit var mAdView : AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private var viewPagerAdapter: PagerAdapter? = null
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)
        supportActionBar?.title = "Paint Art"
        val list_permision = listOf<String>(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        managePermissions = ManagePermissions(this,list_permision,PermissionsRequestCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            managePermissions.checkPermissions()
        mNavigationView.setNavigationItemSelectedListener(this)
        val actionToggle = ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            tool_bar,
            R.string.Drawer_open,
            R.string.Drawer_Close
        )
        mDrawerLayout.addDrawerListener(actionToggle)
        //  mDrawerLayout.openDrawer(Gravity.START)
        actionToggle.syncState()

        GDPR()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        MobileAds.initialize(this)
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "cca-app-pub-3940256099942544/6300978111"
        mAdView = findViewById(R.id.madView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())

            }
        }
            viewPagerAdapter = PagerAdapter(supportFragmentManager)
            viewPagerAdapter!!.addFragment(PhotoFragment(),"Photo")
            viewPagerAdapter!!.addFragment(FavoritFragment(),"Favorite")
            fragment_layout.adapter = viewPagerAdapter
            tabs.setupWithViewPager(fragment_layout)
            tabs.setTabTextColors(Color.parseColor("#84AFAB"),Color.parseColor("#FFFFFF"))
    }

        private fun replaceFragment(fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit()
        }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.shear->{

            }
        }
        return true
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {

            R.id.shear->{
                val int  = Intent()
                int.action = Intent.ACTION_SEND
                int.type = "text/plain"
                int.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.mustafaalsheghri.paintart")
                mInterstitialAd.adListener = object : AdListener() {
                    override fun onAdClosed() {
                        mInterstitialAd.loadAd(AdRequest.Builder().build())
                        startActivity(Intent(Intent.createChooser(int,"Share text via") ))

                    }
                }
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.")
                    startActivity(Intent(Intent.createChooser(int,"Share text via") ))

                }

            }

        }
        closeDrawer()
        return true
    }
    @SuppressLint("WrongConstant")
    private fun closeDrawer() {
        mDrawerLayout.closeDrawer(Gravity.START)
    }
    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            closeDrawer()
        else

            super.onBackPressed()
    }
    private fun GDPR(){
        // Here is GDPR Simple Code
        val consentInformation = ConsentInformation.getInstance(this)
        val publisherIds = arrayOf("pub-1792271155807105")
        consentInformation.requestConsentInfoUpdate(publisherIds, object :
            ConsentInfoUpdateListener {
            override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {
                // User's consent status successfully updated.
            }

            override fun onFailedToUpdateConsentInfo(errorDescription: String) {
                // User's consent status failed to update.
            }
        })
        var privacyUrl: URL? = null
        try {
            // TODO: Replace with your app's privacy policy URL.
            privacyUrl = URL("https://sites.google.com/view/mustafa-alsheghri-devloper")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            // Handle error.
        }

        form = ConsentForm.Builder(this, privacyUrl)
            .withListener(object : ConsentFormListener() {
                override fun onConsentFormLoaded() {
                    // Consent form loaded successfully.
                    form.show()
                }

                override fun onConsentFormOpened() {
                    // Consent form was displayed.
                }

                override fun onConsentFormClosed(
                    consentStatus: ConsentStatus?, userPrefersAdFree: Boolean?) {
                    // Consent form was closed.
                }

                override fun onConsentFormError(errorDescription: String?) {
                    // Consent form error.
                }
            })
            .withPersonalizedAdsOption()
            .withNonPersonalizedAdsOption()
            .withAdFreeOption()
            .build()
        form.load()

        // ## End Code
    }
}