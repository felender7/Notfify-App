package co.za.theappbrewery.notify.core.models
import com.google.firebase.database.Exclude
import java.io.Serializable

class Community : Serializable{
    @Exclude
    var key: String? = null
    var userId:Int? = null
    var name: String? = null
    var description: String? = null
    var category:String? = null
    var subcategory:String? =null
    var imageUrl:String? =null
    var date_created_at:String? = null

    constructor(){}
    constructor(userId:Int?, name:String?,description:String? , category:String?, subcategory:String?, imageUrl:String, date_created_at:String? ){
        this.userId = userId
        this.name = name
        this.description = description
        this.category = category
        this.subcategory = subcategory
        this.imageUrl = imageUrl
        this.date_created_at = date_created_at
    }

}