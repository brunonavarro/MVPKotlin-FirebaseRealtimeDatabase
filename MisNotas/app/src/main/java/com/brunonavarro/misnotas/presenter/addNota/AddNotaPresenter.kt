package com.brunonavarro.misnotas.presenter.addNota

import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.model.Home.interactor.AddNota.AddNotaInteractor
import com.brunonavarro.misnotas.model.Home.interactor.AddNota.AddNotaInteractorImpl
import com.brunonavarro.misnotas.model.Home.interactor.Home.HomeInteractor
import com.brunonavarro.misnotas.model.Home.interactor.Home.HomeInteractorImpl
import com.brunonavarro.misnotas.ui.addNota.AddNotaContract
import com.brunonavarro.misnotas.ui.addNota.events.AddEventNota
import com.brunonavarro.misnotas.ui.home.HomeContract
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddNotaPresenter(mVista: AddNotaContract.View): AddNotaContract.Presenter {

    private var mView: AddNotaContract.View? = null
    private var mInteractor: AddNotaInteractor?=null

    init {
        this.mView=mVista
        this.mInteractor = AddNotaInteractorImpl()
    }

    override fun onShow() {
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        mView = null
    }

    override fun addNota(notas: Nota) {
        if (setProgress()){
            mInteractor?.addNota(notas)
        }
    }

    private fun setProgress(): Boolean {
        if (mView!=null){
            mView?.disableUIelements()
            return true
        }
        return false
    }

    @Subscribe
    override fun onEventListener(addNotaEvent: AddEventNota) {
        if (mView != null) {

            mView?.enableUIelements()

            when (addNotaEvent.getTypeEvent()) {
                AddEventNota().SUCCESS_ADD -> {
                    mView?.notaAdded()
                    System.out.println("PRESENTER ON EVENT LISTENER - Success add: "+AddEventNota().SUCCESS_ADD)
                }
                AddEventNota().ERROR_SERVER -> {mView?.showError(addNotaEvent.getResMsg())
                    System.out.println("PRESENTER ON EVENT LISTENER - Error Server: "+AddEventNota().ERROR_SERVER)
                }
                AddEventNota().ERROR_MAX_VALUE -> {mView?.maxvalueError(addNotaEvent.getResMsg())
                    System.out.println("PRESENTER ON EVENT LISTENER - ERROR REMOVE: "+AddEventNota().ERROR_MAX_VALUE)
                }
            }
        }
    }
}