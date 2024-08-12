object DataModuleClient: TDataModuleClient
  OldCreateOrder = False
  Left = 875
  Top = 229
  Height = 150
  Width = 215
  object ClientSocket1: TClientSocket
    Active = False
    ClientType = ctNonBlocking
    Port = 0
    OnConnect = ClientSocket1Connect
    OnDisconnect = ClientSocket1Disconnect
    OnRead = ClientSocket1Read
    OnError = ClientSocket1Error
    Left = 23
    Top = 8
  end
end
