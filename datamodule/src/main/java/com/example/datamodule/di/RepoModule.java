package com.example.datamodule.di;

import com.example.datamodule.db.DbSource;
import com.example.datamodule.db.DbSourceImpl;
import com.example.datamodule.mapper.Mapper;
import com.example.datamodule.network.NetworkSource;
import com.example.datamodule.network.NetworkSourceImpl;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public interface RepoModule {

    @Binds
    NetworkSource bindsNetworkSource(NetworkSourceImpl networkSource);

    @Binds
    DbSource bindsDataSource(DbSourceImpl dbSource);

}
