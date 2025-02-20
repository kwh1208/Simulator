//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TDataModuleClient.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TDataModuleClient *DataModuleClient;
//---------------------------------------------------------------------------
__fastcall TDataModuleClient::TDataModuleClient(TComponent* Owner)
        : TDataModule(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TDataModuleClient::ClientSocket1Connect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        if(FormMain->FDABITNet)
            FormMain->FDABITNet->DNSConnect = true;
        else
            FormMain->DNSConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TDataModuleClient::ClientSocket1Disconnect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        if(FormMain->FDABITNet)
            FormMain->FDABITNet->bDisConnect = true;
        else
            FormMain->bDisConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TDataModuleClient::ClientSocket1Error(TObject *Sender,
      TCustomWinSocket *Socket, TErrorEvent ErrorEvent, int &ErrorCode)
{
        ErrorCode = 0;
        switch (ErrorEvent)
        {
            case eeGeneral, eeSend, eeReceive, eeConnect, eeDisconnect, eeAccept, eeLookup:
            {
                if(FormMain->FDABITNet)
                    FormMain->FDABITNet->bDisConnect = true;
                else
                    FormMain->bDisConnect = true;
            }
            break;
        }
        ClientSocket1->Socket->Disconnect(ClientSocket1->Socket->SocketHandle);
}
//---------------------------------------------------------------------------

void __fastcall TDataModuleClient::ClientSocket1Read(TObject *Sender,
      TCustomWinSocket *Socket)
{
        int Size = Socket->ReceiveLength();
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        Socket->ReceiveBuf(Buffer, Size);
        //ReadLANParsing(1, 0, Buffer, Size);
        if(FormMain->FDABITNet)
            FormMain->FDABITNet->ReadParsing(1, 0, Buffer, Size);
        else
            FormMain->ReadParsing(1, 0, Buffer, Size);
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------
 