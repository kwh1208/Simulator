//---------------------------------------------------------------------------

#ifndef TDataModuleClientH
#define TDataModuleClientH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ScktComp.hpp>
//---------------------------------------------------------------------------
class TDataModuleClient : public TDataModule
{
__published:	// IDE-managed Components
        TClientSocket *ClientSocket1;
        void __fastcall ClientSocket1Connect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ClientSocket1Disconnect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ClientSocket1Error(TObject *Sender,
          TCustomWinSocket *Socket, TErrorEvent ErrorEvent,
          int &ErrorCode);
        void __fastcall ClientSocket1Read(TObject *Sender,
          TCustomWinSocket *Socket);
private:	// User declarations
public:		// User declarations
        __fastcall TDataModuleClient(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TDataModuleClient *DataModuleClient;
//---------------------------------------------------------------------------
#endif
