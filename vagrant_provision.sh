#!/usr/bin/env bash

echo ">>> Installing MailHog"

# Download binary from github
wget --quiet -O ~/mailhog https://github.com/mailhog/MailHog/releases/download/v1.0.0/MailHog_linux_amd64

# Make it executable
chmod +x ~/mailhog

# Make it start on reboot
sudo tee /etc/systemd/system/mailhog.service <<EOL
[Unit]
Description=MailHog Service
After=network.service vagrant.mount
[Service]
Type=simple
ExecStart=/usr/bin/env /home/vagrant/mailhog > /dev/null 2>&1 &
User=vagrant
Group=vagrant
[Install]
WantedBy=multi-user.target
EOL

sudo systemctl daemon-reload

# Start on reboot
sudo systemctl enable mailhog.service

# Start background service now
sudo systemctl start mailhog.service
