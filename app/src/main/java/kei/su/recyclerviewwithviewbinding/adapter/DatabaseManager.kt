package kei.su.recyclerviewwithviewbinding.adapter

class DatabaseManager {

    fun getProfile(position: Int) : Profile{
        //Todo: Get data from webservice
        return Profile("wwww.google.ca", "Abby", "How are you")
    }

}
