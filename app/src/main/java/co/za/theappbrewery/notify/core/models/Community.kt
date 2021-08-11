package co.za.theappbrewery.notify.core.models

import com.google.firebase.database.Exclude
import java.io.Serializable


class Community {
    @Exclude
    var key: String? = null
    var userId:Int? = null
    var title: String? = null
    var description: String? = null
    var category:String? =null
    var subcategory:String? =null
    var location:String?=null
    private var imageUrl:String?=null


    constructor() {}
    constructor(userId:Int? , title: String?, description: String?, category:String? ,subcategory:String,location:String,imageUrl: String?) {
        this.userId = userId
        this.title = title
        this.description = description
        this.category = category
        this.subcategory = subcategory
        this.location = location
        this.imageUrl = imageUrl
    }
}