package grey.smarthouse.di

import dagger.Module
import dagger.Provides
import grey.smarthouse.App
import grey.smarthouse.data.DataSource
import grey.smarthouse.data.remote.Requests
import javax.inject.Qualifier

@Module
class AppModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @RemoteDataSource
    @Provides
    fun provideRemoteDataSource(): DataSource {
        return Requests
    }

    @LocalDataSource
    @Provides
    fun provideLocalDataSource(): DataSource {
        return App.app.database
    }
}