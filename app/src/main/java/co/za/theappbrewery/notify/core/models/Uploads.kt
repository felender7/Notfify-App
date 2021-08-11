package co.za.theappbrewery.notify.core.models

import com.google.firebase.database.Exclude
import java.io.Serializable

class UploadComImageUrl: Serializable {
    @Exclude
    var uploadId: String? = null

    constructor() {}
    constructor(uploadId: String){
        this.uploadId = uploadId
    }

}

