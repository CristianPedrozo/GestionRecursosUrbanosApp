import com.example.recolectar_app.model.camion.CamionModel
import java.util.ArrayList

class DeleteCamionRequestModel {
    var actionType : String = "delete"
    var entities = ArrayList<CamionModel>()

    fun addEntitie(entitie: CamionModel){
        entities.add(entitie)
    }
}