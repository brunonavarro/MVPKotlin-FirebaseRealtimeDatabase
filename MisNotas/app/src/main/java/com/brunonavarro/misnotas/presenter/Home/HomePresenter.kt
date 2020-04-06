package com.brunonavarro.misnotas.presenter.Home

import com.brunonavarro.misnotas.model.Home.entidad.Nota
import com.brunonavarro.misnotas.model.Home.interactor.Home.HomeInteractor
import com.brunonavarro.misnotas.model.Home.interactor.Home.HomeInteractorImpl
import com.brunonavarro.misnotas.ui.home.HomeContract
import com.brunonavarro.misnotas.ui.home.events.HomeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HomePresenter(mVista: HomeContract.View) :HomeContract.Presenter{

    private var mView: HomeContract.View? = null
    private var mInteractor: HomeInteractor?=null

    init {
        this.mView=mVista
        this.mInteractor = HomeInteractorImpl()
    }

    override fun onCreate() {
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        mInteractor?.unsubscribeToNotas()
    }

    override fun onResume() {
        mInteractor?.subscribeToNotas()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        mView = null
    }

    override fun remove(nota: Nota) {
        if (setProgress()) {
            mInteractor?.removeNotas(nota)
        }
    }

    private fun setProgress(): Boolean {
        if (mView != null) {
            mView?.showProgress()
            return true
        }
        return false
    }

    @Subscribe
    override fun onEventListener(event: HomeEvent) {
        if (mView != null) {
            mView?.hideProgress()

            when (event.getTypeEvent()) {
                HomeEvent().SUCCESS_ADD -> {
                    mView?.add(event.getNotas())
                    System.out.println("PRESENTER ON EVENT LISTENER - Success add: "+HomeEvent().SUCCESS_ADD)
                }
                HomeEvent().SUCCESS_UPDATE -> {mView?.update(event.getNotas())
                    System.out.println("PRESENTER ON EVENT LISTENER - Success update: "+HomeEvent().SUCCESS_UPDATE)
                }
                HomeEvent().SUCCESS_REMOVE -> {mView?.remove(event.getNotas())
                    System.out.println("PRESENTER ON EVENT LISTENER - Success remove: "+HomeEvent().SUCCESS_REMOVE)
                }
                HomeEvent().ERROR_SERVER -> {mView?.onShowError(event.getResMsg())
                    System.out.println("PRESENTER ON EVENT LISTENER - Error Server: "+HomeEvent().ERROR_SERVER)
                }
                HomeEvent().ERROR_TO_REMOVE -> {mView?.removeFail()
                    System.out.println("PRESENTER ON EVENT LISTENER - ERROR REMOVE: "+HomeEvent().ERROR_TO_REMOVE)
                }
            }
        }
    }

}