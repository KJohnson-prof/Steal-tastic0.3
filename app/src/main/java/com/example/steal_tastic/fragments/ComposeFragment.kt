package com.example.steal_tastic.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.steal_tastic.Post
import com.example.steal_tastic.R
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File

class ComposeFragment : Fragment() {


    lateinit var ivPreview: ImageView
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    val photoFileName = "photo.jpg"
    var photoFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPreview = view.findViewById(R.id.ivFile)

        view.findViewById<Button>(R.id.btnSubmit).setOnClickListener{
            val description = view.findViewById<EditText>(R.id.etDescription).text.toString()
            val user = ParseUser.getCurrentUser()

            val itemName = view.findViewById<EditText>(R.id.etItem).text.toString()
            val address = view.findViewById<EditText>(R.id.etAddress).text.toString()
            var tags = view.findViewById<EditText>(R.id.etTag).text.toString()

            // tag string to arraylist block
            val tagList = arrayListOf<String>()
            val reps = tags.filter{it == ';'}.count()
            var index = tags.indexOf(";")
                for (i in 0..reps) {
                    if(index == -1)
                        tagList[i] = tags
                    else {
                        tagList[i] = tags.substring(0, index)
                        tags = tags.substring(index+1, tags.length)
                    }
                    index = tags.indexOf(";")
                }
            /**
             * So I found the amount of occurrences of the separators made the loop run that may time
             * I found the index of the separator to make a substring to add to the arraylist
             * the last word won't have an index of the separator so when that happens "when index is -1" just adds the word to the arraylist
             */
            if(photoFile != null) {
                submitPost(description, user, photoFile!!, itemName, address, tagList)
            }else{

            }
        }
        view.findViewById<Button>(R.id.btnPic).setOnClickListener{
            onLaunchCamera()
        }
    }

    fun submitPost(description: String, user: ParseUser, file: File, itemName: String, address: String, tags: ArrayList<String>){
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))

        post.setItemName(itemName)
        post.setAddress(address)
        post.setTagList(tags)

        post.saveInBackground(){exception ->
            if(exception != null){
                Log.e(TAG, "Error while saving post")
                exception.printStackTrace()
            }else{
                Log.i(TAG, "Successfully saved post")
            }
        }
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPreview.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(requireContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        val TAG = "ComposeFragment"
    }

}