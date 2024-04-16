package xyz.needpainkiller.lib.ssh;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SshService {

    public SSHClient connectClient(SSHClient sshClient, String host, Integer port, String userName) throws IOException {
        return connectClient(sshClient, host, port, userName, null);
    }

    public SSHClient connectClient(SSHClient sshClient, String host, Integer port, String userName, String password) throws IOException {

        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(host, port);
        if (Strings.isBlank(password)) {
            sshClient.authPublickey(userName);
        } else {
            sshClient.authPassword(userName, password);
        }
        return sshClient;
    }

}
