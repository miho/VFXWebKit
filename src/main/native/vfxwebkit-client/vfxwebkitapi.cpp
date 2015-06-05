#include "vfxwebkitapi.h"

#include "vfxbindings.h"
#include "jnitypeconverter.h"

#include <iostream>


VFXWebKitApi::VFXWebKitApi()
{
    //
}

//void VFXWebKitApi::newPage(int key, JNIEnv* env, jobject nativeBinding) {
//    VFXWebPage* page;

////    MyThread thread;

////    thread.setPage(&page);

////    thread.start();
////    thread.wait();

//    page->setJEnv(env);
//    page->setNativeBinding(env, nativeBinding);
//    page->setJVM(env);
//    pages.insert(key, page);
//}

//void VFXWebKitApi::deletePage(int i) {

//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        //std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return;
//    }

//    pages.remove(i);
//    delete page;
//}

//int VFXWebKitApi::pageBufferSize(int i){
//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return -1;
//    }

//    return page->pageBufferSize();
//}

//uchar* VFXWebKitApi::pageBuffer(int i) {
//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return NULL;
//    }

//    return page->pageBuffer();
//}

//void VFXWebKitApi::setSize(int i, int x, int y) {
//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return;
//    }

//    std::cout << "NATIVE: set size" << std::endl;

////    page->setViewportSize(QSize(x,y));
//    page->setMinPageBufferSize(x,y);
//}

//int VFXWebKitApi::getSizeX(int i) {
//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return -1;
//    }

//    return page->viewportSize().width();
//}

//int VFXWebKitApi::getSizeY(int i) {
//    VFXWebPage* page = pages.value(i, NULL);

//    if (page==NULL) {
//        std::cerr << "ERROR: page " << i << " not available!" << std::endl;
//        return -1;
//    }

//    return page->viewportSize().height();
//}

//void VFXWebKitApi::setNativeBinding(JNIEnv* env, jobject nativeBinding) {
//    this->nativeBinding = env->NewGlobalRef(nativeBinding);
//}








#include <vqt_shared_memory.hpp>


VFXWebKitApi api;

bool dirty;

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_init
  (JNIEnv *env, jobject obj) {

}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    newPage
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_newPage
  (JNIEnv * env, jobject o, jint key) {
    //api.newPage(key, env, o);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    deletePage
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_deletePage
  (JNIEnv * env, jobject o, jint key) {
    std::cout << "NATIVE: delete page" << std::endl;
    //api.deletePage(key);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    pageBuffer
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_pageBuffer
  (JNIEnv * env, jobject o, jint key) {

//    std::cout << "NATIVE: page buffer - begin -" << std::endl;


    using namespace boost::interprocess;

    //boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:info:0");
    //boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:buffer:0");

    //Open the shared memory object.
    shared_memory_object shm_info
    (open_only                    //only create
       ,"vqt_shared_memory:page:info:0"              //name
       ,read_write  //read-write mode
    );

    //Map the whole shared memory in this process
    boost::interprocess::mapped_region info_region
            (shm_info                       //What to map
             ,read_write   //Map it as read-write
    );

    //Get the address of the mapped region
    void * info_addr       = info_region.get_address();

    //Construct the shared structure in memory
    vqt_shared_memory_info * info_data = static_cast<vqt_shared_memory_info*>(info_addr);

    info_data->mutex.lock();

    dirty = info_data->dirty;

    //Create a shared memory object.
    boost::interprocess::shared_memory_object shm_buffer
            (open_only               //only create
             ,"vqt_shared_memory:page:buffer:0"   //name
             ,read_write   //read-write mode
    );

    //Map the whole shared memory in this process
    boost::interprocess::mapped_region buffer_region
            (shm_buffer    //What to map
             ,read_write   //Map it as read-write
    );

    //Get the address of the mapped region
    void * buffer_addr       = buffer_region.get_address();

    //Construct the shared structure in memory
    uchar* buffer_data = (uchar*) buffer_addr;

    /*
    if (jvm == NULL || env==NULL || nativeBinding == NULL) {
        std::cerr << "JavaVM, JNIEnv or native binding not initialized!" << std::endl;
        return;
    }

    //JNIEnv* env = threading::attachThread(jvm);

    jmethodID redrawM = env->GetMethodID(
                env->FindClass("eu/mihosoft/vfxwebkit/NativeBinding"),
                "redraw",
                "(IIIII)V");

    env->CallVoidMethod(nativeBinding, redrawM,
                            dirtyRect.x(), dirtyRect.y(),
                            dirtyRect.width(), dirtyRect.height());

////    threading::detachThread(jvm);

    std::cout << "NATIVE: repaint end" << std::endl;

//    uchar* buffer = api.pageBuffer(key);
   */

//    std::cout << "NATIVE: page buffer - end -" << std::endl;

    jbyteArray result =  ucharArray2JByteArray(env, buffer_data,1024*768*4);

    // return env->NewDirectByteBuffer(buffer_data, 800*600*4);

    info_data->mutex.unlock();

    return result;
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    pageBufferSize
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_pageBufferSize
  (JNIEnv * env, jobject o, jint key) {

    std::cout << "NATIVE: page buffer size" << std::endl;

    //return api.pageBufferSize(key);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    setSize
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_setSize
  (JNIEnv * env, jobject o, jint key, jint w, jint h) {

    std::cout << "NATIVE: set size" << std::endl;

    //api.setSize(key,w,h);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    getSizeX
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_getSizeX
   (JNIEnv * env, jobject o, jint key) {

    std::cout << "NATIVE: get size x" << std::endl;

    //return api.getSizeX(key);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    getSizeW
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_getSizeW
   (JNIEnv * env, jobject o, jint key) {

    std::cout << "NATIVE: get size page w" << std::endl;

    //return api.getSizeY(key);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    resizePage
 * Signature: (III)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_resizePage
  (JNIEnv *env, jobject o, jint key, jint w, jint h) {

//    std::cout << "NATIVE: resize page" << std::endl;

    //api.setSize(key,w,h);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    isDirty
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_isDirty
  (JNIEnv *env, jobject obj, jint key) {
    return boolC2J(dirty);
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    setDirty
 * Signature: (IZ)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_setDirty
  (JNIEnv *env, jobject obj, jint key, jboolean state) {
    dirty = state;
}

/*
 * Class:     eu_mihosoft_vfxwebkit_NativeBinding
 * Method:    load
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_eu_mihosoft_vfxwebkit_NativeBinding_load
  (JNIEnv *env, jobject obj, jstring url) {

}
