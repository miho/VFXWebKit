#ifndef VFXWEBKITAPI_H
#define VFXWEBKITAPI_H

//#include <QHash>
//#include <QString>

// instead of Qt stuff, we use plain c++ & boost
// for the client lib
// therefore, we need to declare uchar (was provided by qt before)
typedef unsigned char uchar;


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
