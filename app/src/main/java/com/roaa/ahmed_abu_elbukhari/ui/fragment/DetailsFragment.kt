package com.roaa.ahmed_abu_elbukhari.ui.fragment
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.databinding.FragmentDetailsBinding
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class DetailsFragment : Fragment() {

    private lateinit var detailsBinding: FragmentDetailsBinding
    private val detailsViewModel:
            MainViewModel by navGraphViewModels(R.id.nav_graph)
    private val args: DetailsFragmentArgs by navArgs()


    private lateinit var mInterstitialAd: InterstitialAd


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        detailsViewModel.getMealDetailsByName(args.mealName)
        setupActionBar(requireActivity())
        detailsBinding.lifecycleOwner = this


        buildInterstitialAds()


        return detailsBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.mealDetails.observe(viewLifecycleOwner){ data->

            val ingredientKey =  filterIngredientKey(data = data)
            val measureKey = filterMeasureKey(data = data)

            GlobalScope.launch(Dispatchers.Main) {
                async {
                    setToolbarTitle(data["strMeal"] ?: error(""))
                }.await()


                async {
                    loadImageFromUrl(data["strMealThumb"] ?: error(""))
                }.await()

                async {
                    setMealCountry(data["strArea"] ?: error(""))
                }.await()

                async {
                    setMealCategory(data["strCategory"] ?: error(""))
                }.await()

                async {
                    setMealInstructions(data["strInstructions"] ?: error(""))
                }.await()

                async {
                    setIngredientMeal(ingredientKey = ingredientKey, data = data)
                }.await()

                async {
                    setMeasureMeal(measureKey = measureKey, data = data)
                }.await()


                async {
                    detailsBinding.fab.setOnClickListener {
                        showInterstitial()
                        launchYoutube(data["strYoutube"] ?: error(""))
                    }
                }.await()

            }

        }
    }



    private fun setupActionBar(activity: Activity) {
        (activity as AppCompatActivity?)!!.setSupportActionBar(detailsBinding.detailsToolbar)

        detailsBinding.collapsingToolbar.apply {
            this.setContentScrimColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorWhite
                )
            )
            /**لما يكون مطوي يكون لون العنوان ايه
             * */
            this.setCollapsedTitleTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimary
                )
            )
            /**لما يكون مفرود يكون لون التكست اللي على الصورة ايه
             * */
            this.setExpandedTitleColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorWhite
                )
            )
        }

        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

    }


    private fun filterIngredientKey(data: Map<String, String?>):Set<String>{
        return data.filter{ (ket, value)->
            ket.contains("strIngredient") && value != null && value != "" && value != " "
        }.keys
    }


    private fun filterMeasureKey(data: Map<String, String?>):Set<String>{
        return data.filter { (key, value)->
            key.contains("strMeasure") && value != "" && value != " " && value != null
        }.keys
    }





    private fun launchYoutube(url: String){
        requireContext().startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
    }

    private fun loadImageFromUrl(imageUrl: String){
        Glide.with(requireContext())
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(detailsBinding.header)
        progressBar.visibility = View.GONE
    }


   private fun setMealCategory(mealCategory: String){
        detailsBinding.category.text = mealCategory
    }


    private fun setToolbarTitle(toolbarTitle: String){
        detailsBinding.collapsingToolbar.title = toolbarTitle
    }


    private fun setMealCountry(mealCountry: String){
        detailsBinding.country.text = mealCountry
    }


    private fun setMealInstructions(mealInstructions: String){
        detailsBinding.instructions.text = mealInstructions
    }


    private fun createMeasureBuilder(measureKey: Set<String>, data: Map<String, String?>):StringBuilder{
        val measureBuilder = StringBuilder()
        measureKey.forEach {
            measureBuilder.append(": ${data[it]} \n")
        }
        return measureBuilder
    }


    private fun setMeasureMeal(measureKey: Set<String>, data: Map<String, String?>){
        detailsBinding.measure.text = createMeasureBuilder(measureKey, data)
    }


    private fun createIngredientBuilder(ingredientKey: Set<String>, data: Map<String, String?>):StringBuilder{
        val ingredientBuilder = StringBuilder()
        ingredientKey.forEach {
            ingredientBuilder.append("${"\u2022"} ${data[it]}\n")
        }
        return ingredientBuilder
    }


    private fun setIngredientMeal(ingredientKey: Set<String>, data: Map<String, String?>){
        detailsBinding.ingredient.text = createIngredientBuilder(ingredientKey, data)
    }





    private fun buildInterstitialAds() {
        mInterstitialAd = InterstitialAd(context)
        mInterstitialAd.adUnitId = "ca-app-pub-2511142364873147/8401514878"
        val adRequest = AdRequest.Builder()
            .build()
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest)
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

    }



    private fun showInterstitial() {
     if (mInterstitialAd.isLoaded){
         mInterstitialAd.show()
     }
    }



}