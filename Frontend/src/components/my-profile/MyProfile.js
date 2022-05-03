
function MyProfile(props) {
      
    return (
        <div className="body-replica" style={{color: "#797979", background: "#ddd", fontFamily: "'Open Sans', sans-serif", padding: "0px !important", margin: "0px !important", fontSize: "13px", textRendering: "optimizeLegibility", WebkitFontSmoothing: "antialiased", MozFontSmoothing: "antialiased", MarginTop: "20px"}}>
            <div className="container bootstrap snippets bootdey">
                <div className="row">
                <div className="panel" style={{width: "100%", marginBottom: "20px"}}>
                    <div className="cover-photo" style={{position: 'relative'}}>
                        <div className="fb-timeline-img">
                            <img src="https://bootdey.com/img/Content/bg1.jpg" alt="" style={{width: '100%', height: 'auto', maxHeight: '280px', borderRadius: '4px 4px 0 0', WebkitBorderRadius: '4px 4px 0 0'}} />
                        </div>
                        <div className="fb-name" style={{bottom: '5px', left: '175px', position: 'absolute'}}>
                            <h2><a href="#" style={{color: '#FFFFFF', textRendering: 'optimizelegibility', textShadow: '0 0 3px rgba(0, 0, 0, 0.8)', fontSize: '25px'}}>Deyson Bejarano</a></h2>
                        </div>
                    </div>

                    <div className="panel-body" style={{background: '#fff', padding: "15px"}}>

                        <div className="profile-thumb" style={{float: 'left', position: 'relative'}}>
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" style={{width: '140px', height: '140px', borderRadius: '50%', WebkitBorderRadius: '50%', marginTop: '-90px', border: '3px solid #fff'}} />
                        </div>

                        <div style={{height: 50}}></div>
                    </div>
                </div>

                <div className="panel profile-info" style={{width: "100%", marginBottom: '20px'}}>
                    <form>
                     <textarea className="form-control input-lg p-text-area" rows={2} placeholder="Whats in your mind today?" style={{border: 'none', fontWeight: 300, boxShadow: 'none', color: '#c3c3c3', fontSize: '16px'}} defaultValue={""} />
                    </form>

                    <footer className="panel-footer" style={{"padding":"10px 15px","backgroundColor":"#f5f5f5","borderTop":"1px solid #ddd","borderBottomRightRadius":"3px","borderBottomLeftRadius":"3px"}}>
                        <button type="button" className="btn btn-info pull-right" style={{float: "right !important"}}>Post</button>

                        <ul className="nav nav-pills">
                            <li><a href="#" style={{color: '#7a7a7a'}}><i className="fa fa-map-marker" /></a></li>
                            <li><a href="#" style={{color: '#7a7a7a'}}><i className="fa fa-camera" /></a></li>
                            <li><a href="#" style={{color: '#7a7a7a'}}><i className=" fa fa-film" /></a></li>
                            <li><a href="#" style={{color: '#7a7a7a'}}><i className="fa fa-microphone" /></a></li>
                        </ul>
                    </footer>
                </div>

                <div className="panel">
                    <div className="panel-body" style={{background: '#fff', padding: "15px"}}>
                    <div className="fb-user-thumb" style={{float: 'left', width: '70px', marginRight: '15px'}}>
                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="" style={{width: '70px', height: '70px', borderRadius: '50%', WebkitBorderRadius: '50%'}} />
                    </div>

                    <div className="fb-user-details">
                        <h3 style={{margin: '15px 0 0', fontSize: '18px', fontWeight: 300}}><a href="#" className="#">Margarita Elina</a></h3>
                        <p style={{color: '#c3c3c3'}}>7 minutes ago near Alaska, USA</p>
                    </div>

                    <div className="clearfix" />
                        <p className="fb-user-status" style={{padding: '10px 0', lineHeight: '20px'}}>John is world famous professional photographer.  with forward thinking clients to create beautiful, honest and amazing things that bring positive results. John is world famous professional photographer.  with forward thinking clients to create beautiful, honest and amazing things that bring positive results.
                        </p>
                        <div className="fb-status-container fb-border" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px'}}>
                            <div className="fb-time-action" style={{padding: '15px 0'}}>
                            <a href="#" title="Like this" style={{marginRight: '5px', color: '#2972a1'}}>Like</a>
                            <span style={{marginRight: '5px', color: '#5a5a5a'}}>-</span>
                            <a href="#" title="Leave a comment" style={{marginRight: '5px', color: '#2972a1'}}>Comments</a>
                            <span style={{marginRight: '5px', color: '#5a5a5a'}}>-</span>
                            <a href="#" title="Send this to friend or post it on your time line" style={{marginRight: '5px', color: '#2972a1'}}>Share</a>
                        </div>
                    </div>

                    <div className="fb-status-container fb-border fb-gray-bg" style={{borderTop: '1px solid #ebeef5', margin: '0 -15px 0 -15px', padding: '0 15px', background: '#f6f6f6'}}>
                        <div className="fb-time-action like-info" style={{padding: '15px 0'}}>
                            <a href="#" style={{marginRight: '5px', color: '#2972a1'}}>Jhon Due,</a>
                            <a href="#" style={{marginRight: '5px', color: '#2972a1'}}>Danieal Kalion</a>
                            <span style={{marginRight: '5px', color: '#5a5a5a'}}>and</span>
                            <a href="#" style={{marginRight: '5px', color: '#2972a1'}}>40 more</a>
                            <span style={{marginRight: '5px', color: '#5a5a5a'}}>like this</span>
                        </div>

                        <ul className="fb-comments" style={{listStyleType: 'none'}}>
                            <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                                <a href="#" className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                                <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="" style={{width: '50px'}} /></a>
                                <div className="cmt-details" style={{paddingTop: '5px'}}>
                                <a href="#" style={{fontSize: '14px', fontWeight: 'bold'}}>Jhone due</a>
                                <span> is world famous professional photographer.  with forward thinking clients to create beautiful, </span>
                                <p>40 minutes ago - <a href="#" className="like-link" style={{fontSize: '12px', fontWeight: 'normal'}}>Like</a></p>
                                </div>
                            </li>
                            <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                                <a href="#" className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                                <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="" style={{width: '50px'}} /></a>
                                <div className="cmt-details" style={{paddingTop: '5px'}}>
                                <a href="#" style={{fontSize: '14px', fontWeight: 'bold'}}>Tawseef</a>
                                <span> is world famous professional photographer.  with forward thinking clients to create beautiful, </span>
                                <p>34 minutes ago - <a href="#" className="like-link" style={{fontSize: '12px', fontWeight: 'normal'}}>Like</a></p>
                                </div>
                            </li>
                            <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                                <a href="#" className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                                <img src="https://bootdey.com/img/Content/avatar/avatar4.png" alt="" style={{width: '50px'}} /></a>
                                <div className="cmt-details" style={{paddingTop: '5px'}}>
                                <a href="#" style={{fontSize: '14px', fontWeight: 'bold'}}>Jhone due</a>
                                <span> is world famous professional photographer. </span>
                                <p>15 minutes ago - <a href="#" className="like-link" style={{fontSize: '12px', fontWeight: 'normal'}}>Like</a></p>
                                </div>
                            </li>
                            <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                                <a href="#" className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                                <img src="https://bootdey.com/img/Content/avatar/avatar5.png" alt="" style={{width: '50px'}} /></a>
                                <div className="cmt-details" style={{paddingTop: '5px'}}>
                                <a href="#" style={{fontSize: '14px', fontWeight: 'bold'}}>Tawseef</a>
                                <span> thinking clients to create beautiful world famous professional photographer.</span>
                                <p>2 minutes ago - <a href="#" className="like-link" style={{fontSize: '12px', fontWeight: 'normal'}}>Like</a></p>
                                </div>
                            </li>
                            <li style={{margin: '0 -15px 0 -15px', padding: '15px', borderTop: '1px solid #ebeef5'}}>
                                <a href="#" className="cmt-thumb" style={{width: '50px', float: 'left', marginRight: '15px'}}>
                                <img src="https://bootdey.com/img/Content/avatar/avatar8.png" alt="" style={{width: '50px'}} /></a>

                                <div className="cmt-form" style={{display: 'inline-block', width: '90%'}}>
                                    <textarea className="form-control" placeholder="Write a comment..." name style={{height: '50px', lineHeight: '35px'}} defaultValue={""} />
                                </div>
                            </li>
                        </ul>
                        <div className="clearfix" /></div>
                    </div>
                </div>
                
                </div>
            </div>
        </div>
    )
}

export default MyProfile;