package sn.lahadproject.gestiondeproduits.ModelData

data class ModelVoiture(
    var name: String ,
    var description: String ,
    var energie: String,
    var transmission: String,
    var Image: ByteArray
){
    var id: Int = -1
    constructor(id: Int, name: String, description: String, energie: String, transmission: String, Image: ByteArray): this(name, description, energie, transmission, Image){
        this.id = id
    }
}
