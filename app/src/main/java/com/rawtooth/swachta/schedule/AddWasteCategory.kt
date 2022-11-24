package com.rawtooth.swachta.schedule

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rawtooth.swachta.UploadRequestBodyCategory
import com.rawtooth.swachta.databinding.ActivityAddWasteCategoryBinding
import com.rawtooth.swachta.getFileName
import com.rawtooth.swachta.snackbar
import com.rawtooth.swachta.tokn
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class AddWasteCategory : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityAddWasteCategoryBinding
    private var selectImage: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityAddWasteCategoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.ngoBtnBrowse.setOnClickListener(this)
        binding.ngoSubmit.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        if(selectImage==null){
            binding.layoutRoot.snackbar("Select image first")
            return
        }
        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectImage!!,"r",null)?: return
        val file = File(cacheDir,contentResolver.getFileName(selectImage!!))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        binding.progressBar.progress=0
        val name=binding.ngoName.text.toString()
        val description=binding.ngoDescription.text.toString()
        val userId="1"
        val categoryName=binding.ngotype.selectedItem.toString()
        val wasteImage=UploadRequestBodyCategory(file,"image",this)
        MyApi().uplodImage("Bearer $tokn",
            MultipartBody.Part.createFormData("wasteImage",file.name,wasteImage),
            RequestBody.create(MediaType.parse("multipart/form-data"), description),
            RequestBody.create(MediaType.parse("multipart/form-data"), name),
            RequestBody.create(MediaType.parse("multipart/form-data"), userId),
            RequestBody.create(MediaType.parse("multipart/form-data"), categoryName))
            .enqueue(object :retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    binding.progressBar.progress=100
                    Log.d("code",response.body().toString())
                    binding.layoutRoot.snackbar(response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("code",t.message.toString())
                }
            })
    }


    override fun onClick(view: View?) {
        Intent(Intent.ACTION_PICK).also {
            it.type="image/*"
            val mimeType=arrayOf("image/jpeg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES,mimeType)
            startActivityForResult(it, AddWasteCategory.REQUEST_CODE_PICK_IMAGE)
        }
    }

    fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress=percentage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            when(requestCode){
                AddWasteCategory.REQUEST_CODE_PICK_IMAGE ->{
                    selectImage =data?.data
                    binding.ngoViewImg.setImageURI(selectImage)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}