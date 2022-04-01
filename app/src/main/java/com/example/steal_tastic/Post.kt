package com.example.steal_tastic

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import org.json.JSONArray

@ParseClassName("Post")

class Post() : ParseObject() {
    constructor(parcel: Parcel) : this() {
    }

    fun getImage(): ParseFile?{
        return getParseFile(KEY_IMAGE)
    }
    fun setImage(parsefile: ParseFile){
        put(KEY_IMAGE, parsefile)
    }

    fun getUser(): ParseUser?{
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getItemName(): String?{
        return getString(KEY_ITEMNAME)
    }
    fun setItemName(itemName: String){
        put(KEY_ITEMNAME, itemName)
    }

    fun getAddress(): String?{
        return getString(KEY_ADDRESS)
    }
    fun setAddress(address: String){
        put(KEY_ADDRESS, address)
    }

    fun getDescription(): String?{
        return getString(KEY_DESCRIPTION)
    }
    fun setDescription(description: String){
        put(KEY_DESCRIPTION, description)
    }

    fun getTagList(): JSONArray?{
        return getJSONArray(KEY_TAGLIST)
    }
    fun setTagList(tagList: ArrayList<String>){
        put(KEY_TAGLIST, tagList)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }

        val KEY_IMAGE = "image"
        val KEY_USER = "user"
        val KEY_ITEMNAME = "itemName"
        val KEY_ADDRESS = "address"
        val KEY_DESCRIPTION = "description"
        val KEY_TAGLIST = "tagList"
    }
}