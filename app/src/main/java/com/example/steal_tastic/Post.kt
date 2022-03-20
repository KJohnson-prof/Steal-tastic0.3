package com.example.steal_tastic

import android.os.Bundle
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

class Post : ParseObject() {
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

    companion object{
        const val KEY_IMAGE = "image"
        const val KEY_USER = "user"
        const val KEY_ITEMNAME = "itemName"
        const val KEY_ADDRESS = "address"
        const val KEY_DESCRIPTION = "description"
        const val KEY_TAGLIST = "tagList"
    }
}