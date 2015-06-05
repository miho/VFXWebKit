#include <QApplication>
#include"vfxwebpage.h"

#include <vqt_shared_memory.hpp>

int main(int argc, char *argv[])
{
    using namespace boost::interprocess;

    boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:info:0");
    boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:buffer:0");

    // create the shared memory info object.
    boost::interprocess::shared_memory_object shm_info(
                create_only,
                "vqt_shared_memory:page:info:0",
                read_write
    );

    // set the shm size
    shm_info.truncate(sizeof(vqt_shared_memory_info));

    // map the shared memory info object in this process
    boost::interprocess::mapped_region info_region(shm_info,read_write);

    // get the adress of the info object
    void* info_addr = info_region.get_address();

    // construct the shared structure in memory
    vqt_shared_memory_info* info_data = new (info_addr) vqt_shared_memory_info;


    // create the shared memory buffer object.
    boost::interprocess::shared_memory_object shm_buffer(
                create_only,
                "vqt_shared_memory:page:buffer:0",
                read_write
    );

    // set the size of the shared image buffer (w*h*#channels*sizeof(uchar))
    shm_buffer.truncate( /*w*/1024*/*h*/768
                        */*#channels*/4
                        */*channel size*/sizeof(uchar)
    );

    // map the shared memory buffer object in this process
    boost::interprocess::mapped_region buffer_region(
                shm_buffer,
                read_write
    );

    // get the address of the shared image buffer
    void * buffer_addr = buffer_region.get_address();

    // cast shared memory pointer to correct uchar type
    uchar* buffer_data = (uchar*) buffer_addr;

    // create qapplication before touching any other qobject/class
    QApplication a(argc, argv, false);

    // create demo webpage
    VFXWebPage page;

    // set shared memory objects for page object
    page.setSharedMem(info_data);
    page.setSharedMemBuffer(buffer_data);

    // qapplication does its magic...
    int exec_result = a.exec();

    // remove shared memory objects
    boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:info:0");
    boost::interprocess::shared_memory_object::remove("vqt_shared_memory:page:buffer:0");

    return exec_result;
}
