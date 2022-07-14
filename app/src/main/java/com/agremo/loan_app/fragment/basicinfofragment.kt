package com.agremo.loan_app.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.agremo.loan_app.R
import com.agremo.loan_app.data.basicdetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_basicinfofragment.*
import java.util.*


class basicinfofragment : Fragment(R.layout.fragment_basicinfofragment) {

    lateinit var state : String
    lateinit var district : String
    lateinit var gender : String
    lateinit var districtAdapter : ArrayAdapter<CharSequence>

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseDatabase.getInstance()
        val refrence = db.reference.child("Users")

        var genderAdaper = ArrayAdapter.createFromResource(requireContext() , R.array.array_malefemale, R.layout.spinner_layout)
        genderAdaper.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)

        spinner_malefemale.adapter = genderAdaper

        spinner_malefemale.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                   gender = spinner_malefemale.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        var stateAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.array_states, R.layout.spinner_layout )
        stateAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
        spinner_states.adapter = stateAdapter

        spinner_states.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                 state = spinner_states.selectedItem.toString()


                val parentid = parent.id

                if(parentid == R.id.spinner_states)
                when (state) {
                    "Select Your State" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_default_districts, R.layout.spinner_layout
                    )
                    "Andhra Pradesh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_andhra_pradesh_districts, R.layout.spinner_layout
                    )
                    "Arunachal Pradesh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout
                    )
                    "Assam" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_assam_districts, R.layout.spinner_layout
                    )
                    "Bihar" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_bihar_districts, R.layout.spinner_layout
                    )
                    "Chhattisgarh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_chhattisgarh_districts, R.layout.spinner_layout
                    )
                    "Goa" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_goa_districts, R.layout.spinner_layout
                    )
                    "Gujarat" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_gujarat_districts, R.layout.spinner_layout
                    )
                    "Haryana" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_haryana_districts, R.layout.spinner_layout
                    )
                    "Himachal Pradesh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_himachal_pradesh_districts, R.layout.spinner_layout
                    )
                    "Jharkhand" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_jharkhand_districts, R.layout.spinner_layout
                    )
                    "Karnataka" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_karnataka_districts, R.layout.spinner_layout
                    )
                    "Kerala" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_kerala_districts, R.layout.spinner_layout
                    )
                    "Madhya Pradesh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_madhya_pradesh_districts, R.layout.spinner_layout
                    )
                    "Maharashtra" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_maharashtra_districts, R.layout.spinner_layout
                    )
                    "Manipur" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_manipur_districts, R.layout.spinner_layout
                    )
                    "Meghalaya" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_meghalaya_districts, R.layout.spinner_layout
                    )
                    "Mizoram" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_mizoram_districts, R.layout.spinner_layout
                    )
                    "Nagaland" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_nagaland_districts, R.layout.spinner_layout
                    )
                    "Odisha" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_odisha_districts, R.layout.spinner_layout
                    )
                    "Punjab" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_punjab_districts, R.layout.spinner_layout
                    )
                    "Rajasthan" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_rajasthan_districts, R.layout.spinner_layout
                    )
                    "Sikkim" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_sikkim_districts, R.layout.spinner_layout
                    )
                    "Tamil Nadu" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_tamil_nadu_districts, R.layout.spinner_layout
                    )
                    "Telangana" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_telangana_districts, R.layout.spinner_layout
                    )
                    "Tripura" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_tripura_districts, R.layout.spinner_layout
                    )
                    "Uttar Pradesh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_uttar_pradesh_districts, R.layout.spinner_layout
                    )
                    "Uttarakhand" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_uttarakhand_districts, R.layout.spinner_layout
                    )
                    "West Bengal" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_west_bengal_districts, R.layout.spinner_layout
                    )
                    "Andaman and Nicobar Islands" -> districtAdapter =
                        ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.array_andaman_nicobar_districts, R.layout.spinner_layout
                        )
                    "Chandigarh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_chandigarh_districts, R.layout.spinner_layout
                    )
                    "Dadra and Nagar Haveli" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout
                    )
                    "Daman and Diu" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_daman_diu_districts, R.layout.spinner_layout
                    )
                    "Delhi" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_delhi_districts, R.layout.spinner_layout
                    )
                    "Jammu and Kashmir" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_jammu_kashmir_districts, R.layout.spinner_layout
                    )
                    "Lakshadweep" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_lakshadweep_districts, R.layout.spinner_layout
                    )
                    "Ladakh" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_ladakh_districts, R.layout.spinner_layout
                    )
                    "Puducherry" -> districtAdapter = ArrayAdapter.createFromResource(
                        parent.getContext(),
                        R.array.array_puducherry_districts, R.layout.spinner_layout
                    )
                    else -> {}
                }

                districtAdapter.setDropDownViewResource(androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item)
                spinner_districts.adapter = districtAdapter


                spinner_districts.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        district = spinner_districts.selectedItem.toString()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }



        basicinfo_continue_btn.setOnClickListener {

            val name = bai_et1.text.toString()
            val dateofbirth = bai_et2.text.toString()
            val emailadress = bai_et4.text.toString()
            val streetadress = bai_et5.text.toString()
            val city = bai_et8.text.toString()
            val pincode = bai_et9.text.toString()

            val data = basicdetails(
                name, dateofbirth, gender, emailadress, streetadress, state, district, city, pincode
            )


            if (checkdata() == true) {
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("BasicData")
                    .setValue(data).addOnCompleteListener {
                        Toast.makeText(context, "Data Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()

                    }
                refrence.child((FirebaseAuth.getInstance().currentUser?.phoneNumber).toString())
                    .child("ApplicationStatus")
                    .setValue("basicdetailsfilled")

                Navigation.findNavController(view)
                    .navigate(R.id.action_basicinfofragment_to_kycinformationfragment)
            } else {

                Toast.makeText(context , "Empty or Invalid Data" , Toast.LENGTH_LONG).show()

            }
        }

        bai_et2.setOnClickListener {

            val c = Calendar.getInstance()
            val cyear = c.get(Calendar.YEAR)
            val cmonth = c.get(Calendar.MONTH)
            val cday = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener
            { view, year, month, day ->

                bai_et2.text = "$day-${month + 1}-$year"

            }, cyear, cmonth, cday
            ).show()

        }


        clearFindViewByIdCache()
    }

    private fun checkdata():Boolean {

        var a = true

        if(bai_et1.text.isNullOrEmpty()) {
            bai_et1.error = "Required"
            a = false
        }

        if(bai_et2.text.isNullOrEmpty()) {
            bai_et2.error = "Required"
            a = false
        }

        if(gender == "Gender") {

            Toast.makeText(requireContext() , "Please Select Gender" , Toast.LENGTH_LONG).show()
            a = false
        }

        if(bai_et4.text.isNullOrEmpty()) {
            bai_et4.error = "Required"
            a = false
        }

        if(bai_et5.text.isNullOrEmpty()) {
            bai_et5.error = "Required"
            a = false
        }
        if(state == "Select Your State") {
           bai_tv1.error = "Required"
            a = false
        }

        if(district == "Select Your District") {
            bai_tv2.error = "Required"
           a = false
        }
        if(bai_et8.text.isNullOrEmpty()) {
            bai_et8.error = "Required"
            a = false
        }

        if(bai_et9.text.isNullOrEmpty()) {
            bai_et9.error = "Required"
            a = false
        }

        return a

    }


}