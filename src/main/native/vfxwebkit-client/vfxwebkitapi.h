#ifndef VFXWEBKITAPI_H
#define VFXWEBKITAPI_H

#include <QHash>
#include <QString>

class VFXWebKitApi
{

public:
    VFXWebKitApi();
    //void newPage(int key, JNIEnv* env, jobject nativeBinding);
    void deletePage(int key);
    int pageBufferSize(int key);
    uchar* pageBuffer(int key);
    void setSize(int key, int x, int y);
    int getSizeX(int key);
    int getSizeY(int key);
    //void setNativeBinding(JNIEnv* env, jobject nativeBinding);
//private:
    //QHash<int, VFXWebPage*> pages;
    //jobject         nativeBinding;
};


#endif // VFXWEBKITAPI_H
